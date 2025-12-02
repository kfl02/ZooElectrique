package nl.rumfumme.gl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL32;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import nl.rumfumme.gl.enums.*;

import static com.badlogic.gdx.utils.BufferUtils.*;

public class ShaderInspector {
    public static ShaderInspector INSTANCE = new ShaderInspector();
    
    public interface NamedVariable {
        
    }

    public class Attribute {
        public String name;
        public Type type;
        public int size;
        public int location;
        public int bufferBinding;
        public boolean enabled;
        public int stride;
        Type arrayType;
        public int arraySize;
        public boolean normalized;
        public float[] current;
        EnumSet<ShaderType> referencedBy = EnumSet.noneOf(ShaderType.class);
    }
    
    public class Uniform {
        public String name;
        public Type type;
        public int arraySize;
        public int blockIndex;
        public int location;
        public int offset;
        public int arrayStride;
        public int matrixStride;
        public int atomicCounterBufferIndex;
        public boolean isRowMajor;
        EnumSet<ShaderType> referencedBy = EnumSet.noneOf(ShaderType.class);
        UniformBlock block;
    }
    
    public class UniformBlock {
        public String name;
        public int bufferDataSize;
        public int bufferBinding;
        EnumSet<ShaderType> referencedBy = EnumSet.noneOf(ShaderType.class);
        public Uniform[] uniforms;
    }
    
    public class BufferVariable {
        public String name;
        public Type type;
        public int arraySize;
        public int blockIndex;
        public int location;
        public int offset;
        public int arrayStride;
        public int matrixStride;
        public int topLevelArraySize;
        public int topLevelArrayStride;
        public boolean isRowMajor;
        EnumSet<ShaderType> referencedBy = EnumSet.noneOf(ShaderType.class);
        ShaderStorageBlock block;
    }
    
    public class ShaderStorageBlock {
        public String name;
        public int bufferDataSize;
        public int bufferBinding;
        EnumSet<ShaderType> referencedBy = EnumSet.noneOf(ShaderType.class);
        public BufferVariable[] variables;
    }
    
    public class Shader {
        ShaderType type;
        boolean deleteStatus;
        boolean compileStatus;
        String log;
        String source;
    }

    public class Program {
        public Shader[] shaders;
        public Attribute[] inputs;
        public Attribute[] outputs;
        public Uniform[] uniforms;
        public UniformBlock[] uniformBlocks;
        public BufferVariable[] bufferVariables;
        public ShaderStorageBlock[] shaderStorageBlocks;
        public int[] computeWorkGroupSize;
        public boolean deleteStatus;
        public boolean linkStatus;
        public boolean validateStatus;
        public boolean binaryRetrievable;
        public boolean separable;
        public int transformFeedbackVaryings;
        public String infoLog;
        public TransformFeedbackBufferMode transformFeedbackBufferMode;
    }
        
    private static Map<ResourceProperty, Object> getProgramResource(ShaderProgram shaderP, ProgramInterface programInterface, int idx, EnumSet<ResourceProperty> properties) {
        EnumSet<ResourceProperty> props = EnumSet.copyOf(properties);
        
        props.removeIf(p -> !p.allowedInterfaces.contains(programInterface));
        
        boolean hasActiveVariables = props.contains(ResourceProperty.ACTIVE_VARIABLES);
        boolean hasName = props.contains(ResourceProperty.NAME);
        int propSize = props.size() - (hasName ? 1 : 0);
        IntBuffer propBuf = newIntBuffer(propSize);
        
        // ACTIVE_VARIABLES must come last, otherwise other properties may overwrite the variable indices.
        // NAME properties are not OpenGL but pseudo properties, they will be handled later
        for(ResourceProperty p : props) {
            if(p == ResourceProperty.ACTIVE_VARIABLES || p == ResourceProperty.NAME) {
                continue;
            }

            propBuf.put(p.GL);
        }
        
        if(hasActiveVariables) {
            propBuf.put(ResourceProperty.ACTIVE_VARIABLES.GL);
        }
        
        // for no apparent reason, we have to reset the position of the buffer
        propBuf.position(0);

        // From https://docs.gl/es3/glGetProgramResource:
        //
        //   For the property GL_ACTIVE_VARIABLES, an array of active variable indices associated with an active uniform block,
        //   shader storage block, atomic counter buffer or transform feedback buffer is written to params. 
        //   The number of values written to params for an active resource is given by the value of the property GL_NUM_ACTIVE_VARIABLES
        //   for the resource.
        //
        // So we have to query GL_NUM_ACTIVE_VARIABLES directly and then reserve space for the number of variables.

        int numActiveVariables = 0;
        int paramSize = props.size();
        IntBuffer lengthBuf = newIntBuffer(1);
        
        if(hasActiveVariables) {
            IntBuffer outBuf = newIntBuffer(1);
            IntBuffer inBuf = newIntBuffer(1);
            
            outBuf.put(0, ResourceProperty.NUM_ACTIVE_VARIABLES.GL);
            Gdx.gl32.glGetProgramResourceiv(shaderP.getHandle(), programInterface.GL, idx, outBuf, lengthBuf, inBuf);
            
            numActiveVariables = inBuf.get(0);
            
            paramSize += numActiveVariables - 1;
        }

        IntBuffer paramBuf = newIntBuffer(paramSize);

        Gdx.gl32.glGetProgramResourceiv(shaderP.getHandle(), programInterface.GL, idx, propBuf, lengthBuf, paramBuf);

        Map<ResourceProperty, Object> map = new HashMap<ResourceProperty, Object>(props.size());

        paramBuf.position(0);

        // Can't loop over the EnumSet, because order might be broken because of ACTIVE_VARIABLES and of pseudo-property NAME.
        for(int i = 0; i < propBuf.capacity(); i++) {
            ResourceProperty p = ResourceProperty.valueOf(propBuf.get(i));

            switch(p) {
                case TYPE:
                    map.put(p, Type.valueOf(paramBuf.get()));
                    break;
                    
                case REFERENCED_BY_COMPUTE_SHADER:
                case REFERENCED_BY_FRAGMENT_SHADER:
                case REFERENCED_BY_GEOMETRY_SHADER:
                case REFERENCED_BY_TESS_CONTROL_SHADER:
                case REFERENCED_BY_TESS_EVALUATION_SHADER:
                case REFERENCED_BY_VERTEX_SHADER:
                case IS_PER_PATCH:
                case IS_ROW_MAJOR:
                    map.put(p, paramBuf.get() == 1);
                    break;
                    
                case ACTIVE_VARIABLES:
                    int[] arr = new int[numActiveVariables];

                    paramBuf.get(arr, 0, numActiveVariables);
                    map.put(p, arr);
                    break;
                    
                default:
                    map.put(p, paramBuf.get());
                    break;
            }
        }
        
        // get NAME pseudo-property if requested and available
        if(hasName && programInterface != ProgramInterface.ATOMIC_COUNTER_BUFFER && programInterface != ProgramInterface.TRANSFORM_FEEDBACK_BUFFER) {
            String name = Gdx.gl32.glGetProgramResourceName(shaderP.getHandle(), programInterface.GL, idx);
            
            map.put(ResourceProperty.NAME, name);
        }
        
        return map;
    }

    private static int getProgramInterfaceProperty(ShaderProgram shaderP, ProgramInterface programInterface, InterfaceProperty pname) {
        IntBuffer intBuf = newIntBuffer(1);
        
        // It is an error to specify GL_MAX_NAME_LENGTH when programInterface is GL_ATOMIC_COUNTER_BUFFER, as active atomic counter buffer resources are not assigned name strings.
        if(programInterface == ProgramInterface.ATOMIC_COUNTER_BUFFER && pname == InterfaceProperty.MAX_NAME_LENGTH) {
            return 0;
        }
        
        // When pname is GL_MAX_NUM_ACTIVE_VARIABLES, programInterface must be GL_UNIFORM_BLOCK, GL_ATOMIC_COUNTER_BUFFER, or GL_SHADER_STORAGE_BLOCK.
        if(pname == InterfaceProperty.MAX_NUM_ACTIVE_VARIABLES && !EnumSet.of(ProgramInterface.UNIFORM_BLOCK, ProgramInterface.ATOMIC_COUNTER_BUFFER, ProgramInterface.SHADER_STORAGE_BLOCK).contains(programInterface)) {
            return 0;
        }
        
        Gdx.gl32.glGetProgramInterfaceiv(shaderP.getHandle(), programInterface.GL, pname.GL, intBuf);
        
        return intBuf.get(0);
    }
    
    private static Map<InterfaceProperty, Integer> getProgramInterface(ShaderProgram shaderP, ProgramInterface interf) {
        Map<InterfaceProperty, Integer> map = new HashMap<InterfaceProperty, Integer>();
        
        for(InterfaceProperty p : InterfaceProperty.values()) {
            int r = getProgramInterfaceProperty(shaderP, interf, p);
            
            if(r != 0) {
                map.put(p, r);
            }
        }

        return map;
    }
    
    private static Map<ProgramInterface, Map<InterfaceProperty, Integer>> getProgramInterfaceProperties(ShaderProgram shaderP) {
        Map<ProgramInterface, Map<InterfaceProperty, Integer>> map = new HashMap<ProgramInterface, Map<InterfaceProperty,Integer>>();

        for(ProgramInterface i : ProgramInterface.values()) {
            Map<InterfaceProperty, Integer> r = getProgramInterface(shaderP, i);
            
            if(!r.isEmpty()) {
                map.put(i, r);
            }
        }
        
        return map;
    }
    
    private static Map<ProgramParameter, Object> getProgramParameters(ShaderProgram shaderP) {
        IntBuffer intBuf = newIntBuffer(3);
        Map<ProgramParameter, Object> map = new HashMap<ProgramParameter, Object>();
        
        for(ProgramParameter p : ProgramParameter.values()) {
            Gdx.gl32.glGetProgramiv(shaderP.getHandle(), p.GL, intBuf);
            
            switch(p) {
                case COMPUTE_WORK_GROUP_SIZE:
                    int[] arr = new int[3];
                    arr[0] = intBuf.get(0);
                    arr[1] = intBuf.get(1);
                    arr[2] = intBuf.get(2);
                    
                    map.put(p, arr);
                    break;

                case DELETE_STATUS:
                case LINK_STATUS:
                case PROGRAM_BINARY_RETRIEVABLE_HINT:
                case PROGRAM_SEPARABLE:
                case VALIDATE_STATUS:
                    map.put(p, intBuf.get(0) == GL32.GL_TRUE);
                    break;
                    
                case TRANSFORM_FEEDBACK_BUFFER_MODE:
                    map.put(p, TransformFeedbackBufferMode.valueOf(intBuf.get(0)));
                    break;
                    
                default:
                    map.put(p, intBuf.get(0));
                    break;
            }
        }
        
        return map;
    }
    
    
    public static Shader[] getShaders(ShaderProgram shaderP, int n) {
        IntBuffer intBuf = newIntBuffer(1);
        IntBuffer shaderBuf = newIntBuffer(n);
        Shader[] shaders = new Shader[n];
        
        Gdx.gl32.glGetAttachedShaders(shaderP.getHandle(), n, intBuf, shaderBuf);
        
        for(int i = 0; i < n; i++) {
            shaders[i] = INSTANCE.new Shader();
            
            Gdx.gl32.glGetShaderiv(shaderBuf.get(i), ShaderParameter.SHADER_TYPE.GL, intBuf);
            
            shaders[i].type = ShaderType.valueOf(intBuf.get(0));
            
            Gdx.gl32.glGetShaderiv(shaderBuf.get(i), ShaderParameter.DELETE_STATUS.GL, intBuf);
            
            shaders[i].deleteStatus = intBuf.get(0) == GL32.GL_TRUE;
            
            Gdx.gl32.glGetShaderiv(shaderBuf.get(i), ShaderParameter.COMPILE_STATUS.GL, intBuf);
            
            shaders[i].compileStatus = intBuf.get(0) == GL32.GL_TRUE;
            shaders[i].log = Gdx.gl32.glGetShaderInfoLog(shaderBuf.get(i));
            
            // cannot get source, missing GL call glGetShaderSource
        }
        
        return shaders;
    }
    
    public static Attribute[] getAttributes(Map<Integer, Map<ResourceProperty, Object>> map) {
        Attribute[] attributes = new Attribute[map.keySet().size()];
        
        for(int i : map.keySet()) {
            Attribute a = INSTANCE.new Attribute();
            attributes[i] = a;
            
            // handle resources retrieved by glGetProgramResource() first
            for(Entry<ResourceProperty, Object> p : map.get(i).entrySet()) {
                Object v = p.getValue();

                switch(p.getKey()) {
                    case NAME:
                        a.name = (String)v;
                        break;

                    case TYPE:
                        a.type = (Type)v;
                        break;

                    case ARRAY_SIZE:
                        // is different from ARRAY_SIZE retrieved by glGetVertexAttribute()
                        a.size = (int)v;
                        break;

                    case LOCATION:
                        a.location = (int)v;
                        break;

                    case REFERENCED_BY_COMPUTE_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.COMPUTE_SHADER);
                        break;

                    case REFERENCED_BY_FRAGMENT_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.FRAGMENT_SHADER);
                        break;

                    case REFERENCED_BY_GEOMETRY_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.GEOMETRY_SHADER);
                        break;

                    case REFERENCED_BY_TESS_CONTROL_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.TESS_CONTROL_SHADER);
                        break;

                    case REFERENCED_BY_TESS_EVALUATION_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.TESS_EVALUATION_SHADER);
                        break;

                    case REFERENCED_BY_VERTEX_SHADER:
                        if((boolean)v) a.referencedBy.add(ShaderType.VERTEX_SHADER);
                        break;

                    default:
                        break;
                }
            }
            
            // get other parameters vie glGetVertexAttrib
            IntBuffer intBuf = newIntBuffer(1);
            FloatBuffer floatBuf = newFloatBuffer(4);

            // theoretically can be retrieved by glGetProgramResource(), but the documentation says it's not allowed
            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_BUFFER_BINDING.GL, intBuf);
            
            a.bufferBinding = intBuf.get(0);

            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_ENABLED.GL, intBuf);
            
            a.enabled = intBuf.get(0) != 0;
            
            // is different from ARRAY_SIZE retrieved by glGetProgramResource()
            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_SIZE.GL, intBuf);

            a.arraySize = intBuf.get(0);

            // theoretically can be retrieved by glGetProgramResource(), but the documentation says it's not allowed
            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_STRIDE.GL, intBuf);

            a.stride = intBuf.get(0);
            
            // different from TYPE, e.g. if TYPE == FLOAT_VEC4, then ARRAY_TYPE == FLOAT
            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_TYPE.GL, intBuf);

            a.arrayType = Type.valueOf(intBuf.get(0));

            Gdx.gl32.glGetVertexAttribiv(i, VertexAttributeParameter.VERTEX_ATTRIB_ARRAY_NORMALIZED.GL, intBuf);

            a.normalized = intBuf.get(0) != 0;

            Gdx.gl32.glGetVertexAttribfv(i, VertexAttributeParameter.CURRENT_VERTEX_ATTRIB.GL, floatBuf);

            a.current = new float[4];
            a.current[0] = floatBuf.get(0);
            a.current[1] = floatBuf.get(1);
            a.current[2] = floatBuf.get(2);
            a.current[3] = floatBuf.get(3);
        }
        
        return attributes;
    }
    
    static Map.Entry<UniformBlock[], Uniform[]> getUniforms(Map<Integer, Map<ResourceProperty, Object>> bMap, Map<Integer, Map<ResourceProperty, Object>> uMap) {
        if(bMap == null) {
            bMap = Collections.emptyMap();
        }

        if(uMap == null) {
            uMap = Collections.emptyMap();
        }

        Uniform[] uniforms = new Uniform[uMap.keySet().size()];
        UniformBlock[] blocks = new UniformBlock[bMap.keySet().size()];
        
        // handle blocks
        for(int i : bMap.keySet()) {
            UniformBlock b = INSTANCE.new UniformBlock();
            blocks[i] = b;
            
            for(Entry<ResourceProperty, Object> p : bMap.get(i).entrySet()) {
                Object v = p.getValue();

                switch(p.getKey()) {
                    case NAME:
                        b.name = (String)v;
                        break;

                    case BUFFER_BINDING:
                        b.bufferBinding = (int)v;
                        break;

                    case BUFFER_DATA_SIZE:
                        b.bufferDataSize = (int)v;
                        break;

                    case NUM_ACTIVE_VARIABLES:
                        b.uniforms = new Uniform[(int)v];
                        break;

                    case REFERENCED_BY_COMPUTE_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.COMPUTE_SHADER);
                        break;

                    case REFERENCED_BY_FRAGMENT_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.FRAGMENT_SHADER);
                        break;

                    case REFERENCED_BY_GEOMETRY_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.GEOMETRY_SHADER);
                        break;

                    case REFERENCED_BY_TESS_CONTROL_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.TESS_CONTROL_SHADER);
                        break;

                    case REFERENCED_BY_TESS_EVALUATION_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.TESS_EVALUATION_SHADER);
                        break;

                    case REFERENCED_BY_VERTEX_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.VERTEX_SHADER);
                        break;

                    default:
                        break;
                }
            }
        }
        
        int[] blkIdx = new int[bMap.entrySet().size()];
        
        for(int i : uMap.keySet()) {
            Uniform u = INSTANCE.new Uniform();
            uniforms[i] = u;
            
            for(Entry<ResourceProperty, Object> p : uMap.get(i).entrySet()) {
                Object v = p.getValue();

                switch(p.getKey()) {
                    case NAME:
                        u.name = (String)v;
                        break;

                    case TYPE:
                        u.type = (Type)v;
                        break;

                    case ARRAY_SIZE:
                        u.arraySize = (int)v;
                        break;

                    case LOCATION:
                        u.location = (int)v;
                        break;

                    case OFFSET:
                        u.offset = (int)v;
                        break;

                    case BLOCK_INDEX:
                        if((int)v > -1) {
                            u.blockIndex = (int)v;
                            blocks[(int)v].uniforms[blkIdx[(int)v]++] = u;
                            u.block = blocks[(int)v];
                        }
                        break;

                    case ARRAY_STRIDE:
                        u.arrayStride = (int)v;
                        break;

                    case MATRIX_STRIDE:
                        u.matrixStride = (int)v;
                        break;
                        
                    case IS_ROW_MAJOR:
                        u.isRowMajor = (boolean)v;
                        break;

                    case ATOMIC_COUNTER_BUFFER_INDEX:
                        u.atomicCounterBufferIndex = (int)v;
                        break;

                    case REFERENCED_BY_COMPUTE_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.COMPUTE_SHADER);
                        break;

                    case REFERENCED_BY_FRAGMENT_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.FRAGMENT_SHADER);
                        break;

                    case REFERENCED_BY_GEOMETRY_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.GEOMETRY_SHADER);
                        break;

                    case REFERENCED_BY_TESS_CONTROL_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.TESS_CONTROL_SHADER);
                        break;

                    case REFERENCED_BY_TESS_EVALUATION_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.TESS_EVALUATION_SHADER);
                        break;

                    case REFERENCED_BY_VERTEX_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.VERTEX_SHADER);
                        break;

                    default:
                        break;
                }
            }
        }
 
        Map.Entry<UniformBlock[], Uniform[]> uniformsAndBlocks = new AbstractMap.SimpleImmutableEntry<>(blocks, uniforms);
        
        return uniformsAndBlocks;
    }

    static Map.Entry<ShaderStorageBlock[], BufferVariable[]>  getShaderStorageBlocks(Map<Integer, Map<ResourceProperty, Object>> bMap, Map<Integer, Map<ResourceProperty, Object>> vMap) {
        if(bMap == null) {
            bMap = Collections.emptyMap();
        }

        if(vMap == null) {
            vMap = Collections.emptyMap();
        }

        BufferVariable[] variables = new BufferVariable[vMap.keySet().size()];
        ShaderStorageBlock[] blocks = new ShaderStorageBlock[bMap.keySet().size()];
        
        // handle blocks
        for(int i : bMap.keySet()) {
            ShaderStorageBlock b = INSTANCE.new ShaderStorageBlock();
            blocks[i] = b;
            
            for(Entry<ResourceProperty, Object> p : bMap.get(i).entrySet()) {
                Object v = p.getValue();

                switch(p.getKey()) {
                    case NAME:
                        b.name = (String)v;
                        break;

                    case BUFFER_BINDING:
                        b.bufferBinding = (int)v;
                        break;

                    case BUFFER_DATA_SIZE:
                        b.bufferDataSize = (int)v;
                        break;

                    case NUM_ACTIVE_VARIABLES:
                        b.variables = new BufferVariable[(int)v];
                        break;

                    case REFERENCED_BY_COMPUTE_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.COMPUTE_SHADER);
                        break;

                    case REFERENCED_BY_FRAGMENT_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.FRAGMENT_SHADER);
                        break;

                    case REFERENCED_BY_GEOMETRY_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.GEOMETRY_SHADER);
                        break;

                    case REFERENCED_BY_TESS_CONTROL_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.TESS_CONTROL_SHADER);
                        break;

                    case REFERENCED_BY_TESS_EVALUATION_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.TESS_EVALUATION_SHADER);
                        break;

                    case REFERENCED_BY_VERTEX_SHADER:
                        if((boolean)v) b.referencedBy.add(ShaderType.VERTEX_SHADER);
                        break;

                    default:
                        break;
                }
            }
        }
        
        int[] blkIdx = new int[bMap.entrySet().size()];
        
        for(int i : vMap.keySet()) {
            BufferVariable u = INSTANCE.new BufferVariable();
            variables[i] = u;
            
            for(Entry<ResourceProperty, Object> p : vMap.get(i).entrySet()) {
                Object v = p.getValue();

                switch(p.getKey()) {
                    case NAME:
                        u.name = (String)v;
                        break;

                    case TYPE:
                        u.type = (Type)v;
                        break;

                    case ARRAY_SIZE:
                        u.arraySize = (int)v;
                        break;

                    case LOCATION:
                        u.location = (int)v;
                        break;

                    case OFFSET:
                        u.offset = (int)v;
                        break;

                    case BLOCK_INDEX:
                        if((int)v > -1) {
                            u.blockIndex = (int)v;
                            blocks[(int)v].variables[blkIdx[(int)v]++] = u;
                            u.block = blocks[(int)v];
                        }
                        break;

                    case ARRAY_STRIDE:
                        u.arrayStride = (int)v;
                        break;

                    case MATRIX_STRIDE:
                        u.matrixStride = (int)v;
                        break;
                        
                    case IS_ROW_MAJOR:
                        u.isRowMajor = (boolean)v;
                        break;
                        
                    case TOP_LEVEL_ARRAY_SIZE:
                        u.topLevelArraySize = (int)v;
                        break;
                        
                    case TOP_LEVEL_ARRAY_STRIDE:
                        u.topLevelArrayStride = (int)v;
                        break;

                    case REFERENCED_BY_COMPUTE_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.COMPUTE_SHADER);
                        break;

                    case REFERENCED_BY_FRAGMENT_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.FRAGMENT_SHADER);
                        break;

                    case REFERENCED_BY_GEOMETRY_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.GEOMETRY_SHADER);
                        break;

                    case REFERENCED_BY_TESS_CONTROL_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.TESS_CONTROL_SHADER);
                        break;

                    case REFERENCED_BY_TESS_EVALUATION_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.TESS_EVALUATION_SHADER);
                        break;

                    case REFERENCED_BY_VERTEX_SHADER:
                        if((boolean)v) u.referencedBy.add(ShaderType.VERTEX_SHADER);
                        break;

                    default:
                        break;
                }
            }
        }
 
        Map.Entry<ShaderStorageBlock[], BufferVariable[]> blocksAndVariables = new AbstractMap.SimpleImmutableEntry<>(blocks, variables);
        
        return blocksAndVariables;
    }
    
    public static Map<StateParameter, Object> getStateParameters() {
        return null;
    }

    public static Program getProgram(ShaderProgram shaderP) {
        Program program = INSTANCE.new Program();
        
        Map<ProgramParameter, Object> parameters = getProgramParameters(shaderP);
        
        program.shaders = getShaders(shaderP, (int)parameters.get(ProgramParameter.ATTACHED_SHADERS));
        program.computeWorkGroupSize = (int[])parameters.get(ProgramParameter.COMPUTE_WORK_GROUP_SIZE);
        program.deleteStatus = (boolean)parameters.get(ProgramParameter.DELETE_STATUS);
        program.linkStatus = (boolean)parameters.get(ProgramParameter.LINK_STATUS);
        program.validateStatus = (boolean)parameters.get(ProgramParameter.VALIDATE_STATUS);
        program.separable = (boolean)parameters.get(ProgramParameter.PROGRAM_SEPARABLE);
        program.binaryRetrievable = (boolean)parameters.get(ProgramParameter.PROGRAM_BINARY_RETRIEVABLE_HINT);
        program.transformFeedbackBufferMode = (TransformFeedbackBufferMode)parameters.get(ProgramParameter.TRANSFORM_FEEDBACK_BUFFER_MODE);
        program.transformFeedbackVaryings = (int)parameters.get(ProgramParameter.TRANSFORM_FEEDBACK_VARYINGS);

        // get all active interface
        Map<ProgramInterface, Map<InterfaceProperty, Integer>> interfaceProperties = getProgramInterfaceProperties(shaderP);
        Map<ProgramInterface, Map<Integer, Map<ResourceProperty, Object>>> maps = new HashMap<ProgramInterface, Map<Integer, Map<ResourceProperty, Object>>>(ProgramInterface.values().length);

        // get all active resources for active interfaces
        for(ProgramInterface i : interfaceProperties.keySet()) {
            int n = interfaceProperties.get(i).get(InterfaceProperty.ACTIVE_RESOURCES);
            
            Map<Integer, Map<ResourceProperty, Object>> map = new HashMap<Integer, Map<ResourceProperty,Object>>(n);
            
            for(int j = 0; j < n; j++) {
                map.put(j, getProgramResource(shaderP, i, j, EnumSet.allOf(ResourceProperty.class)));
            }
            
            maps.put(i, map);
        }

        // extract 
        program.inputs = getAttributes(maps.get(ProgramInterface.PROGRAM_INPUT));
        program.outputs = getAttributes(maps.get(ProgramInterface.PROGRAM_OUTPUT));
        Map.Entry<UniformBlock[], Uniform[]> uniformsAndBlocks = getUniforms(maps.get(ProgramInterface.UNIFORM_BLOCK), maps.get(ProgramInterface.UNIFORM));
        program.uniformBlocks  = uniformsAndBlocks.getKey();
        program.uniforms = uniformsAndBlocks.getValue();
        Map.Entry<ShaderStorageBlock[], BufferVariable[]> storageBlocksAndVariables = getShaderStorageBlocks(maps.get(ProgramInterface.SHADER_STORAGE_BLOCK), maps.get(ProgramInterface.BUFFER_VARIABLE));
        program.shaderStorageBlocks  = storageBlocksAndVariables.getKey();
        program.bufferVariables = storageBlocksAndVariables.getValue();
        
        IntBuffer intBuf = newIntBuffer(1);
        
        Gdx.gl32.glGetIntegerv(GL32.GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS, intBuf);
        System.out.println(intBuf.get(0));
//        Gdx.gl32.glGetBooleani_v(GL32.GL_MAX_COMPUTE_WORK_GROUP_COUNT, intBuf);
//        System.out.println(intBuf.get(0));
        Gdx.gl32.glGetIntegerv(GL32.GL_MAX_COMPUTE_WORK_GROUP_SIZE, intBuf);
        System.out.println(intBuf.get(0));

        return program;
    }
}
