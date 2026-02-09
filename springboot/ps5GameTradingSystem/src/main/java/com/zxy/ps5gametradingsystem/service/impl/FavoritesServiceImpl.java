package com.zxy.ps5gametradingsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.ps5gametradingsystem.entity.Favorites;
import com.zxy.ps5gametradingsystem.mapper.FavoritesMapper;
import com.zxy.ps5gametradingsystem.service.FavoritesService;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {
}
