import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';
import {Structure, StructureService} from "../structure";
import * as jsPDF from 'jspdf';

@Component({
    selector: 'jhi-bulletin-detail',
    templateUrl: './bulletin-detail.component.html'
})
export class BulletinDetailComponent implements OnInit, OnDestroy {

    bulletin: Bulletin;
    structure: Structure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bulletinService: BulletinService,
        private structureService: StructureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBulletins();
        this.getThestructure();
    }

    load(id) {
        this.bulletinService.find(id)
            .subscribe((bulletinResponse: HttpResponse<Bulletin>) => {
                this.bulletin = bulletinResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBulletins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bulletinListModification',
            (response) => this.load(this.bulletin.id)
        );
    }

    getThestructure(){
        this.structureService.findDenom('Novatech - SA')
            .subscribe((structureResponse: HttpResponse<Structure>) => {
                this.structure = structureResponse.body;
            });
    }

    downLoadPdf(){
        let doc = new jsPDF();
        const elementToPrint = document.getElementById ('myBulletin');
        console.log(elementToPrint);
        //doc.text('Liste des Types de produit', 10, 10);
        doc.fromHTML($('#myBulletin').get(0), 20, 20, {
            'width':1000});

        doc.save('senPaieBulletin.pdf');
        /*doc.addHTML(elementToPrint, ()=>{
            doc.save('senPaieBulletin.pdf');
        });
        */

    }
}
