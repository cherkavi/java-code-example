package com.vitaliy.rs485_module;

/**
 * Интерфейс, который содержит метод для вызова из таймера (после наступления события таймера) 
 */
public interface Ievent_timer {
	public void event_timer(String event_unique_name);
}
