package com.chan.music.service.impl;

import com.chan.music.pojo.Music;
import com.chan.music.mapper.MusicDao;
import com.chan.music.service.IMusicService;
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
public class MusicServiceImpl extends ServiceImpl<MusicDao, Music> implements IMusicService {

}
