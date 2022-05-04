package com.zzk.study.library.jackson.serializer;

import java.util.Date;

public class EventWithoutSerializer {
	public String name;
	public Date eventDate;

	public EventWithoutSerializer(String name, Date eventDate) {
		this.name = name;
		this.eventDate = eventDate;
	}
}