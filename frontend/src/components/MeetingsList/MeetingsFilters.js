import React, { useEffect, useState } from "react";
import { useMessage } from "./../../hooks/useMessage";
import { initSelects } from "./../../handler/MaterializeInitializersHandler";
import { DEFAULT_MEETINGS_FILTER } from "./../../constants/defaults";

function MeetingsFilters({handler}) {
    const [sortOption, setSortOption] = useState(DEFAULT_MEETINGS_FILTER.sortBy === "" ? "default" : DEFAULT_MEETINGS_FILTER);
    const [sortOrder, setSortOrder] = useState(DEFAULT_MEETINGS_FILTER.sortOrder);
    const [filter, setFilter] = useState(DEFAULT_MEETINGS_FILTER.select);

    const handleSubmit = event => {
        event.preventDefault();

        handler({
            sortBy: sortOption === "default" ? "" : sortOption,
            sortOrder: sortOrder,
            select: filter
        });
    };

    useEffect(() => {
        initSelects();
    });

    return (
        <div className="filters col s12">
            <form action="" method="get" onSubmit={handleSubmit}>
                <div className="s-hflex">
                    <div className="input-field">
                        <select id="sort-by-option" name="sort-by" defaultValue={sortOption} onChange={event => setSortOption(event.target.value)}>
                            <option value="default" disabled>{useMessage("sort_by")}...</option>
                            <option value="date">{useMessage("by_date")}</option>
                            <option value="users_count">{useMessage("by_users")}</option>
                            <option value="topics_count">{useMessage("by_topics")}</option>
                        </select>
                    </div>
                    <div className="input-field mx10">
                        <select id="sort-order-option" name="sort-order" defaultValue={sortOrder} onChange={event => setSortOrder(event.target.value)}>
                            <option value="asc">{useMessage("ascending")}</option>
                            <option value="desc">{useMessage("descending")}</option>
                        </select>
                    </div>
                    <div className="input-field">
                        <select id="select-option" name="filter-selector" defaultValue={filter} onChange={event => setFilter(event.target.value)}>
                            <option value="all">{useMessage("all")}</option>
                            <option value="past">{useMessage("past")}</option>
                            <option value="future">{useMessage("future")}</option>
                        </select>
                    </div>
                    <div className="s-vflex-center pl10 equal-flex">
                        <button type="submit" className="btn waves-effect orange">
                            {useMessage("confirm")}
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default MeetingsFilters;