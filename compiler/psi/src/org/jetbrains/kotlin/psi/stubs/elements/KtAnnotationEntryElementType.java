/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.psi.stubs.elements;

import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinAnnotationEntryStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class KtAnnotationEntryElementType extends KtStubElementType<KotlinAnnotationEntryStub, KtAnnotationEntry> {

    public KtAnnotationEntryElementType(@NotNull @NonNls String debugName) {
        super(debugName, KtAnnotationEntry.class, KotlinAnnotationEntryStub.class);
    }

    @NotNull
    @Override
    public KotlinAnnotationEntryStub createStub(@NotNull KtAnnotationEntry psi, StubElement parentStub) {
        Name shortName = psi.getShortName();
        String resultName = shortName != null ? shortName.asString() : null;
        KtValueArgumentList valueArgumentList = psi.getValueArgumentList();
        boolean hasValueArguments = valueArgumentList != null && !valueArgumentList.getArguments().isEmpty();
        return new KotlinAnnotationEntryStubImpl((StubElement<?>) parentStub, StringRef.fromString(resultName), hasValueArguments, null);
    }

    @Override
    public void serialize(@NotNull KotlinAnnotationEntryStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getShortName());
        dataStream.writeBoolean(stub.hasValueArguments());
        if (stub instanceof KotlinAnnotationEntryStubImpl) {
            Map<Name, ConstantValue<?>> arguments = ((KotlinAnnotationEntryStubImpl) stub).getValueArguments();
            dataStream.writeVarInt(arguments != null ? arguments.size() : 0);
            if (arguments != null) {
                for (Map.Entry<Name, ConstantValue<?>> valueEntry : arguments.entrySet()) {
                    dataStream.writeName(valueEntry.getKey().asString());
                    ConstantValue<?> value = valueEntry.getValue();
                    KotlinConstantValueKt.serializeConstantValue(value, dataStream);
                }
            }
        }
    }

    @NotNull
    @Override
    public KotlinAnnotationEntryStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        StringRef text = dataStream.readName();
        boolean hasValueArguments = dataStream.readBoolean();
        int valueArgCount = dataStream.readVarInt();
        Map<Name, ConstantValue<?>> args = new LinkedHashMap<>();
        for (int i = 0; i < valueArgCount; i++) {
            args.put(Name.identifier(Objects.requireNonNull(dataStream.readNameString())),
                     KotlinConstantValueKt.deserializeConstantValue(dataStream));
        }
        return new KotlinAnnotationEntryStubImpl((StubElement<?>) parentStub, text, hasValueArguments, args.isEmpty() ? null : args);
    }

    @Override
    public void indexStub(@NotNull KotlinAnnotationEntryStub stub, @NotNull IndexSink sink) {
        StubIndexService.getInstance().indexAnnotation(stub, sink);
    }
}
