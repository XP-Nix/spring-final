package com.example.demo.services;

import com.example.demo.models.ChannelModel;
import com.example.demo.repos.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;


    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public boolean createChannel(ChannelModel channel) {
        ChannelModel newChannel = channel;
        channelRepository.save(newChannel);

        return true;
    }

    public List<ChannelModel> getAllChannels() {
        return channelRepository.findAll();
    }

    public ChannelModel getChannelById(Integer id) {
        return channelRepository.findById(id).orElseThrow(() -> new RuntimeException("Channel not found"));
    }





    public boolean updateChannel(Integer id, ChannelModel channelModel) {
        ChannelModel channel = getChannelById(id);
        channel.setName(channelModel.getName());
        channelRepository.save(channel);
        return true;
    }






    public boolean deleteChannel(Integer id) {
        ChannelModel channel = getChannelById(id);
        channel.setDeleted(true);
        channelRepository.save(channel);
        return true;
    }



}
