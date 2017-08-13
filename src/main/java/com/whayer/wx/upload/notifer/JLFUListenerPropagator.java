package com.whayer.wx.upload.notifer;


import org.springframework.stereotype.Component;

import com.whayer.wx.upload.notifer.utils.GenericPropagator;




/**
 * Propagates the events to the registered listeners.
 * 
 * @author antoinem
 * 
 */
@Component
public class JLFUListenerPropagator extends GenericPropagator<JLFUListener> {

	@Override
	protected Class<JLFUListener> getProxiedClass() {
		return JLFUListener.class;
	}

}
