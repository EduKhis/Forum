package com.example.forum.service;

import com.example.forum.model.ArchiveItem;
import com.example.forum.model.Message;
import com.example.forum.repository.ArchiveItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ArchiveItemService {
    @Autowired
    ArchiveItemRepository archiveItemRepository;
    @Autowired
    MessageService messageService;

    public void save(ArchiveItem archiveItem) {
        archiveItemRepository.save(archiveItem);
    }
    public List<ArchiveItem> findAll () {
        return archiveItemRepository.findAll();
    }
    public boolean existByMessage (Message message) {
        if (archiveItemRepository.existsByMessage(message)) {
            return true;
        }
        return false;
    }
    public List<Message> finalList () {
        List<ArchiveItem> list = archiveItemRepository.findAll();
        List<Message> listAll= messageService.findAll();
        List<Message> listArch = list.stream().map(x->x.getMessage()).toList();
        listAll.removeAll(listArch);
        return listAll;
    }
    public ArchiveItem findById (Long a) {
        return archiveItemRepository.findById(a).get();
    }
    public void deleteById (Long a) {
        archiveItemRepository.deleteById(a);
    }
    public List<Message> sortList (String a, List<Message> listAll) {
        //List<Message> listAll = archiveItemService.finalList();
        if (a.equals("Newest to Oldest")) {
            listAll.sort(Comparator.comparing(Message::getDate));
        }
        if (a.equals("Oldest to Newest")) {
            listAll.sort(Comparator.comparing(Message::getDate).reversed());
        }
        if (a.equals("Name A-Z")) {
            listAll.sort(Comparator.comparing(Message::getUsername));
        }
        if (a.equals("Name Z-A")) {
            listAll.sort(Comparator.comparing(Message::getUsername).reversed());
        }
        return listAll;
    }

}
