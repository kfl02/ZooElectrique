/*
 * Part of the Game Control Plus library - http://www.lagers.org.uk/gamecontrol
 * 
 * Copyright (c) 2014 Peter Lager
 * <quark(a)lagers.org.uk> http:www.lagers.org.uk
 * 
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from
 * the use of this software.
 * 
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it freely,
 * subject to the following restrictions:
 * 
 * 1. The origin of this software must not be misrepresented;
 * you must not claim that you wrote the original software.
 * If you use this software in a product, an acknowledgment in the product
 * documentation would be appreciated but is not required.
 * 
 * 2. Altered source versions must be plainly marked as such,
 * and must not be misrepresented as being the original software.
 * 
 * 3. This notice may not be removed or altered from any source distribution.
 * 
 * 
 * ---------------------------------------------------------------------------------
 * Updated and enhanced from the proCONTROLL library [http://texone.org/procontrol], 
 * copyright (c) 2005 Christian Riekoff which was released under the terms of the GNU 
 * Lesser General Public License (version 2.1 or later) as published by the Free 
 * Software Foundation.
 * ---------------------------------------------------------------------------------
 */

package org.gamecontrolplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import processing.core.PApplet;
import net.java.games.input.Component;

/**
 * This class represents a button of a device. You can use the pressed() 
 * method to see if a button is pressed or use the plug method to 
 * handle events.
 * 
 * @author Christian Riekoff & Peter Lager
 */
public class ControlButton extends ControlInput {
		private boolean pressed = false;
	private boolean oldPressed = false;
	
	protected final Map<EventType, ArrayList<Callback>> plugMap;  

	/**
	 * Initializes a new Button.
	 * @param i_component
	 */
	ControlButton(final Component i_component){
		super(i_component);
		
		inputType = InputType.BUTTON_TYPE;
		plugMap = new HashMap<EventType, ArrayList<Callback>>();

		Arrays.asList(EventType.values()).forEach(type -> plugMap.put(type, new ArrayList<Callback>()));
	}
	
	/**
	 * This method is called before each frame to update the button state.
	 */
	@Override
	void update(){
		actualValue = component.getPollData() * 8;
		pressed = actualValue > 0f;

		if(pressed && oldPressed) {
			callPlugs(EventType.WHILE_PRESS);
		} else if(pressed && !oldPressed){
			callPlugs(EventType.ON_PRESS);
		} else if(!pressed && oldPressed){
			callPlugs(EventType.ON_RELEASE);
		}
		
		oldPressed = pressed;
	}
	
	/**
	 * This method returns true if the button was pressed. 
	 * @return boolean, true if the button was pressed
	 * @usage application
	 * @related ControllButton
	 */
	public boolean pressed(){
		return pressed;
	}

	public void plug(Callback callback, EventType type) {
	    plugMap.get(type).add(callback);
	}

	/**
	 * Call all plugs for this button
	 */
	protected void callPlugs(EventType type) {
	    plugMap.get(type).forEach(plug -> ((ButtonCallback)plug).call());
	}
	
	/**
	 * Get a text description for this button
	 * @param tab
	 */
	public String toText(String tab){
		String name = (actualName.length() > 20 ) ?
				actualName.substring(0, 17) + "..." : (actualName + "                    ").substring(0,  20);
		return tab + "button    " + name + "-";
	}
}
