package com.bharat.latencyfromdb.Utilities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Latency")
public class Latency
{
    private int page;
    private int pageSize;
    private int totalHits;
    private time adjustedTimeframe;
    private List<Item> items;
    //for adjustedTimeFrame
    @NoArgsConstructor
    public static class time
    {
        public Integer windowSize;
        public Long to;
        public time(Integer windowSize, Long to)
        {
            this.windowSize = windowSize;
            this.to = to;
        }
    }
    //for items
    public static class Item
    {
        @NoArgsConstructor
        public static class application
        {
            public String id;
            public String label;
            public String boundaryScope;
            public String entityType;

            public application(String id, String label, String boundaryScope, String entityType)
            {
                this.id = id;
                this.label = label;
                this.boundaryScope = boundaryScope;
                this.entityType = entityType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getBoundaryScope() {
                return boundaryScope;
            }

            public void setBoundaryScope(String boundaryScope) {
                this.boundaryScope = boundaryScope;
            }

            public String getEntityType() {
                return entityType;
            }

            public void setEntityType(String entityType) {
                this.entityType = entityType;
            }
        }

        //for metrics
        @NoArgsConstructor
        public static class metrics
        {
            @JsonProperty("latency.p90")
            public List<List> latencyp90;

            public metrics(List<List> latencyp90) {
                this.latencyp90 = latencyp90;
            }

            public List<List> getLatencyp90() {
                return latencyp90;
            }

            public void setLatencyP90(List<List> latencyp90) {
                this.latencyp90 = latencyp90;
            }
        }
        public application application;
        public metrics metrics;
        /*
        @NoArgsConstructor
        public static class metrics
        {
            public Long[][] latencyp90;

            public metrics(Long[][] latencyp90) {
                this.latencyp90 = latencyp90;
            }

            public Long[][] getLatencyp90() {
                return latencyp90;
            }

            public void setLatencyp90(Long[][] latencyp90) {
                this.latencyp90 = latencyp90;
            }
        }*/
    }
}

