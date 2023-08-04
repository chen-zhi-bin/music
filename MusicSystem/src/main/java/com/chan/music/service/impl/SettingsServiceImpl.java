package com.chan.music.service.impl;

import com.chan.music.pojo.Settings;
import com.chan.music.mapper.SettingsDao;
import com.chan.music.service.ISettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsDao, Settings> implements ISettingsService {

}
