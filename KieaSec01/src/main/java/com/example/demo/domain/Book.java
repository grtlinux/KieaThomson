package com.example.demo.domain;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Book {

	private Long idx;
	private String title;
	private String content;
}
