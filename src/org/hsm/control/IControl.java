package org.hsm.control;

import java.util.logging.Logger;

import org.hsm.view.IView;

public interface IControl {
	void fire(String command, Object... data);

	void fireByView(IView view, String command, Object... data);

	Object getData(String command, Object... data);

	Logger getLogger();
}
