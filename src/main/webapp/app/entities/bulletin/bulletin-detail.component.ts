import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';
import {Structure, StructureService} from "../structure";
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';

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
        let data = document.getElementById ('myBulletin');
        html2canvas(data).then(canvas =>{
            console.log('innnnnnn');
            let imgWidth = 208;
            //let pageHeight = 295;
            let imgHeight = canvas.height * imgWidth / canvas.width;
            //let heightLeft = imgHeight;

            const contentDataURL = canvas.toDataURL('image/png');
            let pdf = new jsPDF('p', 'mm', 'a4');
            pdf.addImage(contentDataURL, 'PNG', 10, 20, imgWidth, imgHeight);
            console.log(this.bulletin);
            console.log(this.bulletin.dateCreated['__proto__']['year']);
            pdf.save(this.bulletin.collaborateur['nom']+this.bulletin.collaborateur['prenom']+this.bulletin.dateCreated+'.pdf');
        });
    }
}
