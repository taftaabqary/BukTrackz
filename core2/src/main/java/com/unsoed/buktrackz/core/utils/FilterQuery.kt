package com.unsoed.buktrackz.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object FilterQuery {
    fun getRawQuery(filter: Filter): SimpleSQLiteQuery {
        val stringBuilder = StringBuilder().append("SELECT * FROM book ")
        when(filter) {
            Filter.NOVEL -> stringBuilder.append("WHERE genre = 'Novel'")
            Filter.FICTION -> stringBuilder.append("WHERE genre = 'Fiction'")
            Filter.FINANCE -> stringBuilder.append("WHERE genre = 'Finance'")
            Filter.HISTORIC -> stringBuilder.append("WHERE genre = 'Historic'")
            Filter.ANTHROPOLOGY -> stringBuilder.append("WHERE genre = 'Anthropology'")
            Filter.DYSTOPIC -> stringBuilder.append("WHERE genre = 'Dystopic'")
            Filter.SELF_GROWTH -> stringBuilder.append("WHERE genre = 'Self Growth'")
            Filter.FANTASY -> stringBuilder.append("WHERE genre = 'Fantasy'")
            else -> stringBuilder.append("")
        }

        return SimpleSQLiteQuery(stringBuilder.toString())
    }
}