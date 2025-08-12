package org.example.bnb.core.di

import coil3.ImageLoader
import org.koin.core.module.Module

// Declaramos que esperamos que cada plataforma nos forneça um módulo Koin
// que saiba como criar um ImageLoader.
expect fun imageLoaderModule(): Module