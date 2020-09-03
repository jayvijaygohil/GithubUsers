package com.jayvijaygohil.githubusers

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.memory.MemoryCache
import coil.request.*

fun getFakeSuccessfullImageLoader(drawable: Drawable = ColorDrawable(Color.BLACK)) =
    object : ImageLoader {
        private val disposable = object : Disposable {
            override val isDisposed = true
            override fun dispose() {}
            override suspend fun await() {}
        }

        override val bitmapPool: BitmapPool = BitmapPool(0)
        override val defaults: DefaultRequestOptions = DefaultRequestOptions()
        override val memoryCache: MemoryCache = object : MemoryCache {
            override val maxSize: Int = 0
            override val size: Int = 0

            override fun get(key: MemoryCache.Key): Bitmap? = null

            override fun remove(key: MemoryCache.Key): Boolean = true

            override fun set(key: MemoryCache.Key, bitmap: Bitmap) {}

            override fun clear() {}
        }

        override fun enqueue(request: ImageRequest): Disposable {
            request.target?.onStart(drawable)
            request.target?.onSuccess(drawable)
            return disposable
        }

        override suspend fun execute(request: ImageRequest): ImageResult {
            return SuccessResult(
                drawable = drawable,
                request = request,
                metadata = ImageResult.Metadata(
                    memoryCacheKey = MemoryCache.Key(""),
                    isSampled = false,
                    dataSource = DataSource.MEMORY_CACHE,
                    isPlaceholderMemoryCacheKeyPresent = false
                )
            )
        }

        override fun shutdown() {}
    }

fun getFakeErrorImageLoader(fallbackDrawable: Drawable? = null) =
    object : ImageLoader {
        private val disposable = object : Disposable {
            override val isDisposed = true
            override fun dispose() {}
            override suspend fun await() {}
        }

        override val bitmapPool: BitmapPool = BitmapPool(0)
        override val defaults: DefaultRequestOptions = DefaultRequestOptions()
        override val memoryCache: MemoryCache = object : MemoryCache {
            override val maxSize: Int = 0
            override val size: Int = 0

            override fun get(key: MemoryCache.Key): Bitmap? = null

            override fun remove(key: MemoryCache.Key): Boolean = true

            override fun set(key: MemoryCache.Key, bitmap: Bitmap) {}

            override fun clear() {}
        }

        override fun enqueue(request: ImageRequest): Disposable {
            request.target?.onStart(fallbackDrawable)
            request.target?.onError(fallbackDrawable)
            return disposable
        }

        override suspend fun execute(request: ImageRequest): ImageResult {
            return ErrorResult(
                drawable = null,
                request = request,
                throwable = IllegalArgumentException("Sorry chief!!!!")
            )
        }

        override fun shutdown() {}
    }