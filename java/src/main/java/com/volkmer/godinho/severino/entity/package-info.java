@XmlJavaTypeAdapters({
	@XmlJavaTypeAdapter(type=LocalDate.class,  value=LocalDateAdapter.class),
})
package com.volkmer.godinho.severino.entity;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import com.volkmer.godinho.core.adapters.LocalDateAdapter;

