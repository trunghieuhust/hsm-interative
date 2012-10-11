package org.hsm.view;

public interface IView {
	void fire(String command, Object... data);
}
