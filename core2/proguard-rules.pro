# Keep adapter classes
-keep class com.unsoed.buktrackz.core.adapter.** { *; }

# Keep data classes and entities
-keep class com.unsoed.buktrackz.core.data.local.Preferences { *; }
-keep class com.unsoed.buktrackz.core.domain.entity.Book { *; }
-keep class com.unsoed.buktrackz.core.domain.entity.Note { *; }

# Keep repository and use case classes
-keep class com.unsoed.buktrackz.core.domain.repository.IBookRepository { *; }
-keep class com.unsoed.buktrackz.core.domain.usecase.BookInteractor { *; }
-keep class com.unsoed.buktrackz.core.domain.usecase.BookUseCase { *; }

# Keep utility classes
-keep class com.unsoed.buktrackz.core.utils.DateConverter { *; }
-keep class com.unsoed.buktrackz.core.utils.Filter { *; }
-keep class com.unsoed.buktrackz.core.utils.ListBook { *; }

# Keep Koin module
-keep class com.unsoed.buktrackz.core.di.CoreModuleKt { *; }

# Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.unsoed.buktrackz.core.**$$serializer { *; }
-keepclassmembers class com.unsoed.buktrackz.core.** {
    *** Companion;
}

# Keep all public and protected methods in these classes
-keepclassmembers class com.unsoed.buktrackz.core.** {
    public protected *;
}

# Keep StringConcatFactory
# Keep StringConcatFactory and related classes
-keep class java.lang.invoke.** { *; }

# Preserve method invocation
-keepattributes MethodParameters,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations

# Additional rules to help with string operations
-dontwarn java.lang.invoke.**

# Preserve StringConcatFactory-related methods
-keepclassmembers class * {
    private static java.lang.String getPercentProgress(int, int);
}

# Additional keep rules for core classes
-keep class com.unsoed.buktrackz.core.adapter.** { *; }
-keepclassmembers class com.unsoed.buktrackz.core.adapter.** {
    *;
}

# Preserve all public and protected methods in adapters
-keepclassmembers class com.unsoed.buktrackz.core.adapter.** {
    public protected *;
}

# Additional general ProGuard rules
-dontwarn java.lang.invoke.**
-dontwarn javax.annotation.**

# Kotlin specific rules
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Keep outer class
-keep class com.unsoed.buktrackz.core.data.response.BookBestSellerResponse { *; }

# Keep response classes
-keep class com.unsoed.buktrackz.core.data.response.BookBestSellerResponse { *; }
-keep class com.unsoed.buktrackz.core.data.response.BooksItem { *; }

# Keep event classes
-keep class com.unsoed.buktrackz.core.utils.Event { *; }

# Keep all members of these classes
-keepclassmembers class com.unsoed.buktrackz.core.utils.Event { *; }

# Keep all members of these classes
-keepclassmembers class com.unsoed.buktrackz.core.data.response.BookBestSellerResponse { *; }
-keepclassmembers class com.unsoed.buktrackz.core.data.response.BooksItem { *; }
# Gson specific rules
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Serialization rules
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.unsoed.buktrackz.core.data.response.**$$serializer { *; }
-keepclassmembers class com.unsoed.buktrackz.core.data.response.** {
    *** Companion;
}