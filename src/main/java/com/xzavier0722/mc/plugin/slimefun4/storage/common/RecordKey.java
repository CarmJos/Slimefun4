package com.xzavier0722.mc.plugin.slimefun4.storage.common;

import io.github.bakedlibs.dough.collections.Pair;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RecordKey extends ScopeKey {
    private final Set<FieldKey> fields;
    private final List<Pair<FieldKey, String>> conditions;

    @ParametersAreNonnullByDefault
    public RecordKey(DataScope scope) {
        this(scope, new HashSet<>());
    }

    @ParametersAreNonnullByDefault
    public RecordKey(DataScope scope, Set<FieldKey> fields) {
        this(scope, fields, new LinkedList<>());
    }

    @ParametersAreNonnullByDefault
    public RecordKey(DataScope scope, Set<FieldKey> fields, List<Pair<FieldKey, String>> conditions) {
        super(scope);
        this.fields = fields.isEmpty() ? fields : new HashSet<>(fields);
        this.conditions = conditions.isEmpty() ? conditions : new LinkedList<>(conditions);
    }

    @ParametersAreNonnullByDefault
    public void addField(FieldKey field) {
        fields.add(field);
    }

    @Nonnull
    public Set<FieldKey> getFields() {
        return Collections.unmodifiableSet(fields);
    }

    @ParametersAreNonnullByDefault
    public void addCondition(FieldKey key, String val) {
        conditions.add(new Pair<>(key, val));
    }

    @Nonnull
    public List<Pair<FieldKey, String>> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    @Override
    protected String getKeyStr() {
        var re = new StringBuilder();
        re.append(scope).append("/");
        conditions.forEach(c -> re.append(c.getFirstValue()).append("=").append(c.getSecondValue()).append("/"));
        fields.forEach(f -> re.append(f).append("/"));
        return re.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof RecordKey other)) {
            return false;
        }

        if (this.scope != other.scope) {
            return false;
        }

        if (this.fields.size() != other.fields.size()) {
            return false;
        }

        var conditionSize = this.conditions.size();
        if (conditionSize != other.conditions.size()) {
            return false;
        }

        for (var field : this.fields) {
            if (!other.fields.contains(field)) {
                return false;
            }
        }

        for (var i = 0; i < conditionSize; i++) {
            if (!this.conditions.get(i).equals(other.conditions.get(i))) {
                return false;
            }
        }

        return true;
    }
}
