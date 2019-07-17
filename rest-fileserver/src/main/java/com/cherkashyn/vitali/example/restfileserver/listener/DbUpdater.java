package com.cherkashyn.vitali.example.restfileserver.listener;

import com.cherkashyn.vitali.example.restfileserver.event.ReceiveFileEvent;
import com.cherkashyn.vitali.example.restfileserver.service.reestr.Reestr;
import com.cherkashyn.vitali.example.restfileserver.service.reestr.ReestrRepository;
import com.cherkashyn.vitali.example.restfileserver.service.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DbUpdater {

    @Autowired
    ReestrRepository repository;

    @EventListener
    public void newFileReceived(ReceiveFileEvent event){
        Reestr record = new Reestr();
        record.setBranchName("");
        record.setOriginalFileName(event.getSource().getOriginalFilename());
        record.setStatus(Reestr.Status.NEW.toString());
        repository.save(record);
        System.out.println(">>> new file received: "+event.getSource().getOriginalFilename());
    }
}
