package com.cherkashin.vitaliy.frame;

public interface ModalClose {
	/** метод, который получает управление, когда потомок возвращает управление из "модального окна" */
	public void close(int return_value);
}
