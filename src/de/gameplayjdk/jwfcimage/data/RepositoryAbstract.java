/*
 * The MIT License (MIT)
 * Copyright (c) 2019 GameplayJDK
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.gameplayjdk.jwfcimage.data;

import de.gameplayjdk.jwfcimage.data.entity.EntityAbstract;
import de.gameplayjdk.jwfcimage.data.id.IdStoreAtomic;
import de.gameplayjdk.jwfcimage.data.id.IdStoreInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RepositoryAbstract<E extends EntityAbstract> implements RepositoryInterface<E> {

    protected final IdStoreInterface id;

    protected final List<E> entityList;

    public RepositoryAbstract() {
        this.id = new IdStoreAtomic();

        this.entityList = new ArrayList<E>();
    }

    @Override
    public abstract E create();

    @Override
    public boolean add(E entity) {
        if (null != entity && null == this.getById(entity.getId())) {
            return this.entityList.add(entity);
        }

        return false;
    }

    @Override
    public E getById(final int id) {
        return this.entityList.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<E> getAll() {
        return Collections.unmodifiableList(this.entityList);
    }

    public void deleteAll() {
        this.entityList.clear();
    }

    protected int getNewId() {
        return this.id.increment();
    }
}
