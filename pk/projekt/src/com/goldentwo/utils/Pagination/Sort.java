package com.goldentwo.utils.Pagination;

public class Sort {
	private String field;
	private Direction direction;
	
	public Sort(String field, Direction direction) {
		this.field = field;
		this.direction = direction;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
