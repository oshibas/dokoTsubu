package model;

import dao.MuttersDAO;

public class DeleteMutterListLogic {
	public void execute(String name, int muId) {
		MuttersDAO dao = new MuttersDAO();
		dao.delete(muId, name);
	}
}