package org.hsm.model;

public interface IModel {
	Object getData(String command, Object... data);

	boolean setData(String command, Object... data);
}
