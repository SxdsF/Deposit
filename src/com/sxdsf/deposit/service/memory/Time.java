package com.sxdsf.deposit.service.memory;

import java.util.concurrent.TimeUnit;

/**
 * 时间存储的基本类型
 * 
 * @author sunbowen
 * 
 */
public class Time {
	public TimeUnit tu;
	public int time;

	/**
	 * 永久性存储的默认time值
	 */
	public static final Time WILL_NOT_INVALID = new Time(TimeUnit.DAYS,
			Integer.MAX_VALUE);

	public Time(TimeUnit tu, int time) {
		this.tu = tu;
		this.time = time;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		boolean result = false;
		if (o != null && o instanceof Time) {
			if (this.tu == ((Time) o).tu && this.time == ((Time) o).time) {
				result = true;
			}
		}
		return result;
	}

}
