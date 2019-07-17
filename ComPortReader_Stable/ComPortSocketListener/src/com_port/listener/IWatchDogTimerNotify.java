package com_port.listener;

/** оповещатель о событиях */
public interface IWatchDogTimerNotify {
	/** оповещение о пришедшем на порт событии */
	public void notifyWatchDog();
}
