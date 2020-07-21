package ru.bisoft.laboratory.guide.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GuideUtils {
    public static Pageable DEFAULT_PAGE;

    @Value("${rest.guide.default-page-size}")
    public void setDefaultPageSize(Integer defaultPageSize) {
        GuideUtils.DEFAULT_PAGE = PageRequest.of(0, defaultPageSize);
    }
}
