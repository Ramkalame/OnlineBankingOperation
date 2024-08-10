package com.onlineBankingOperations.utils;

import com.onlineBankingOperations.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {

    private List<Client> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private boolean lastPage;
}
