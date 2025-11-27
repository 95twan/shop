package com.rodem.shop.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations operations;
    private final ProductSearchRepository productSearchRepository;


    public ProductSearchResponse searchProducts(String keyword, String category, Pageable pageable) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> {
                    if (keyword != null && !keyword.isBlank()) {
                        b.must(
                                m -> m.match(
                                        mm -> mm
                                                .field("name")
                                                .query(keyword)
                                                .operator(Operator.And)
                                )
                        );
                    }
                    if (category != null && !category.isBlank()) {
                        b.filter(f -> f.term(
                                t -> t.field("category").value(category)
                        ));
                    }
                    return b;
                }))
                .withPageable(pageable)
                .build();

        SearchHits<ProductDocument> hits = operations.search(query, ProductDocument.class);
        List<ProductDocument> items = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        return new ProductSearchResponse(hits.getTotalHits(), items);
    }

    public ProductDocument indexProduct(ProductIndexRequest request) {
        Instant updateAt = Instant.now();
        ProductDocument doc = new ProductDocument(
                null,
                request.name(),
                request.brand(),
                request.category(),
                request.price(),
                updateAt
        );
        return productSearchRepository.save(doc);
    }

    public IndexUpdateResponse applyProductIndexConfig(IndexConfigRequest request) {
        IndexOperations ops = operations.indexOps(ProductDocument.class);
        boolean created = false;
        boolean settingsUpdated = false;
        boolean mappingUpdated = false;

        if (!ops.exists()) {
            Document settings = Document.create();
            if (request.numberOfShards() != null) {
                settings.put("index.number_of_shards", request.numberOfShards());
            }
            if (request.numberOfReplicas() != null) {
                settings.put("index.number_of_replicas", request.numberOfReplicas());
            }
            created = ops.create(settings);
            mappingUpdated = ops.putMapping(ops.createMapping(ProductDocument.class));
        } else {
            mappingUpdated = ops.putMapping(ops.createMapping(ProductDocument.class));
        }

        return new IndexUpdateResponse(created, settingsUpdated, mappingUpdated);
    }

    public IndexStatusResponse getProductIndexStatus() {
        IndexOperations ops = operations.indexOps(ProductDocument.class);
        boolean exists = ops.exists();
        Map<String, Object> settings = exists ? new HashMap<>(ops.getSettings()) : Map.of();
        Map<String, Object> mapping = exists ? new HashMap<>(ops.getMapping()) : Map.of();

        return new IndexStatusResponse(exists, settings, mapping);
    }
}
