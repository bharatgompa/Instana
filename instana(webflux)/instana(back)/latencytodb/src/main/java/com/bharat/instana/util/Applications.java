package com.bharat.instana.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "applications")
public class Applications
{
    private int page;
    private int pageSize;
    private int totalHits;
    private link _links;
    private time adjustedTimeframe;
    private List<item> items;
    //for links
    public static class link
    {
        public String self;

        public link(String self) {
            this.self = self;
        }
    }
    //for adjustedTimeFrame
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
    public static class item
    {
        public String id;
        public String label;
        public String boundaryScope;
        public String entityType;
        public item(String id, String label, String boundaryScope, String entityType)
        {
            this.id = id;
            this.label = label;
            this.boundaryScope = boundaryScope;
            this.entityType = entityType;
        }
    }
}
