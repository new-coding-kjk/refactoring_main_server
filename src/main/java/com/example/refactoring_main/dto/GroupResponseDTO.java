package com.example.refactoring_main.dto;

import com.example.refactoring_main.entity.Group;
import lombok.Data;

import java.util.List;
@Data
public class GroupResponseDTO {
    private List<Group> groups;
    private int currentPage;
    private int totalPages;
    private long totalElements;


    public GroupResponseDTO(List<Group> groups, int currentPage, int totalPages, long totalElements) {
        this.groups = groups;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
