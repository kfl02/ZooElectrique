package nl.rumfumme.gl.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.GL32;

public enum ResourceProperty {
    NAME(-1, 
            EnumSet.complementOf(
                    EnumSet.of(
                            ProgramInterface.ATOMIC_COUNTER_BUFFER, 
                            ProgramInterface.TRANSFORM_FEEDBACK_BUFFER
                            )
                    )
            ),
    NAME_LENGTH(GL32.GL_NAME_LENGTH, 
            EnumSet.complementOf(
                    EnumSet.of(
                            ProgramInterface.ATOMIC_COUNTER_BUFFER, 
                            ProgramInterface.TRANSFORM_FEEDBACK_BUFFER
                            )
                    )
            ),
    TYPE(GL32.GL_TYPE, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT,
                ProgramInterface.TRANSFORM_FEEDBACK_VARYING,
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
    ARRAY_SIZE(GL32.GL_ARRAY_SIZE, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT,
                ProgramInterface.TRANSFORM_FEEDBACK_VARYING
                )
            ),
    OFFSET(GL32.GL_OFFSET, EnumSet.of(
                ProgramInterface.UNIFORM, 
                ProgramInterface.BUFFER_VARIABLE, 
                ProgramInterface.TRANSFORM_FEEDBACK_VARYING
                )
            ),
    BLOCK_INDEX(GL32.GL_BLOCK_INDEX, EnumSet.of(
                ProgramInterface.UNIFORM, 
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
    ARRAY_STRIDE(GL32.GL_ARRAY_STRIDE, EnumSet.of(
                ProgramInterface.UNIFORM, 
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
    MATRIX_STRIDE(GL32.GL_MATRIX_STRIDE, EnumSet.of(
                ProgramInterface.UNIFORM, 
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
    IS_ROW_MAJOR(GL32.GL_IS_ROW_MAJOR, EnumSet.of(
                ProgramInterface.UNIFORM, 
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
    ATOMIC_COUNTER_BUFFER_INDEX(GL32.GL_ATOMIC_COUNTER_BUFFER_INDEX, EnumSet.of(
                ProgramInterface.BUFFER_VARIABLE
                )
            ),
//    TEXTURE_BUFFER(GL32.GL_TEXTURE_BUFFER, EnumSet.noneOf(ProgramInterface.class)),
    BUFFER_BINDING(GL32.GL_BUFFER_BINDING, EnumSet.of(
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.TRANSFORM_FEEDBACK_BUFFER
                )
            ),
    BUFFER_DATA_SIZE(GL32.GL_BUFFER_DATA_SIZE, EnumSet.of(
                ProgramInterface.UNIFORM_BLOCK, 
                ProgramInterface.ATOMIC_COUNTER_BUFFER, 
                ProgramInterface.SHADER_STORAGE_BLOCK
                )
            ),
    NUM_ACTIVE_VARIABLES(GL32.GL_NUM_ACTIVE_VARIABLES, EnumSet.of(
                ProgramInterface.UNIFORM_BLOCK, 
                ProgramInterface.ATOMIC_COUNTER_BUFFER, 
                ProgramInterface.SHADER_STORAGE_BLOCK, 
                ProgramInterface.TRANSFORM_FEEDBACK_BUFFER
                )
            ),
    ACTIVE_VARIABLES(GL32.GL_ACTIVE_VARIABLES, EnumSet.of(
                ProgramInterface.UNIFORM_BLOCK, 
                ProgramInterface.ATOMIC_COUNTER_BUFFER, 
                ProgramInterface.SHADER_STORAGE_BLOCK, 
                ProgramInterface.TRANSFORM_FEEDBACK_BUFFER
                )
            ),
    REFERENCED_BY_VERTEX_SHADER(GL32.GL_REFERENCED_BY_VERTEX_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    REFERENCED_BY_TESS_CONTROL_SHADER(GL32.GL_REFERENCED_BY_TESS_CONTROL_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    REFERENCED_BY_TESS_EVALUATION_SHADER(GL32.GL_REFERENCED_BY_TESS_EVALUATION_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    REFERENCED_BY_GEOMETRY_SHADER(GL32.GL_REFERENCED_BY_GEOMETRY_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    REFERENCED_BY_FRAGMENT_SHADER(GL32.GL_REFERENCED_BY_FRAGMENT_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    REFERENCED_BY_COMPUTE_SHADER(GL32.GL_REFERENCED_BY_COMPUTE_SHADER, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.UNIFORM_BLOCK,
                ProgramInterface.ATOMIC_COUNTER_BUFFER,
                ProgramInterface.SHADER_STORAGE_BLOCK,
                ProgramInterface.BUFFER_VARIABLE,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    TOP_LEVEL_ARRAY_SIZE(GL32.GL_TOP_LEVEL_ARRAY_SIZE, EnumSet.of(ProgramInterface.BUFFER_VARIABLE)),
    TOP_LEVEL_ARRAY_STRIDE(GL32.GL_TOP_LEVEL_ARRAY_STRIDE, EnumSet.of(ProgramInterface.BUFFER_VARIABLE)),
    LOCATION(GL32.GL_LOCATION, EnumSet.of(
                ProgramInterface.UNIFORM,
                ProgramInterface.PROGRAM_INPUT,
                ProgramInterface.PROGRAM_OUTPUT
                )
            ),
    // does this belong is ES3?
    IS_PER_PATCH(GL32.GL_IS_PER_PATCH, EnumSet.of(ProgramInterface.PROGRAM_INPUT, ProgramInterface.PROGRAM_OUTPUT))
    // GL_LOCATION_INDEX and GL_LOCATION_COMPONENT are documented but missing
    ;

    public int GL;
    public EnumSet<ProgramInterface> allowedInterfaces;

    private static Map<Integer, ResourceProperty> glMap = new HashMap<Integer, ResourceProperty>();
    
    static {
        for(ResourceProperty t : ResourceProperty.values()) {
            glMap.put(t.GL, t);
        }
    }
    
    public static Map<ProgramInterface, EnumSet<ResourceProperty>> allowedResourcesPerInterface = new HashMap<ProgramInterface, EnumSet<ResourceProperty>>();
    
    static {
        for(ResourceProperty p : ResourceProperty.values()) {
            for(ProgramInterface i : p.allowedInterfaces) {
                if(allowedResourcesPerInterface.containsKey(i)) {
                    allowedResourcesPerInterface.get(i).add(p);
                } else {
                    allowedResourcesPerInterface.put(i, EnumSet.of(p));
                }
            }
        }
    }

    private ResourceProperty(int GL, EnumSet<ProgramInterface> allowedInterfaces) {
        this.GL = GL;
        this.allowedInterfaces = allowedInterfaces;
    }

    public static ResourceProperty valueOf(int GL) {
        return glMap.get(GL);
    }
}
