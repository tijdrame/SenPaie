import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Recap } from './recap.model';
import { RecapService } from './recap.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Exercice, ExerciceService} from "../exercice";
import {BulletinService} from "../bulletin";
import {Bulletin} from "../bulletin/bulletin.model";
import {Collaborateur, CollaborateurService} from "../collaborateur";
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';

@Component({
    selector: 'jhi-recap',
    templateUrl: './recap.component.html'
})
export class RecapComponent implements OnInit, OnDestroy {

currentAccount: any;
    recaps: Recap[] ;
    recapRsult: Recap;
    bulletins: Bulletin[];
    exercice: Exercice;
    exercices: Exercice[];
    collaborateurs: Collaborateur[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private recapService: RecapService,
        private collabService: CollaborateurService,
        private bulletinService: BulletinService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private exerciceService: ExerciceService,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.recaps = [];
        });
    }

    loadAll() {
        /*this.recapService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<Recap[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );*/
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/recap'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/recap', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRecaps();
        this.exerciceService.query()
            .subscribe((res: HttpResponse<Exercice[]>) => { this.exercices = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));

        this.collabService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Recap) {
        return item.id;
    }
    registerChangeInRecaps() {
        this.eventSubscriber = this.eventManager.subscribe('recapListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.recaps = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    search(){

        this.bulletinService.recap(this.exercice)
            .subscribe((res: HttpResponse<Bulletin[]>) =>
                {
                    //this.bulletins =[];
                    this.recaps=[];
                    this.recapRsult = new Recap();
                    this.bulletins = res.body;
                    this.bulletins.forEach((it: Bulletin) => {
                        console.log(it.collaborateur['nom']+" "+it.netAPayer);
                    });
                    this.collaborateurs.forEach((eachCollab: Collaborateur) => {
                        console.log("collab="+eachCollab.prenom+" nom="+eachCollab.nom);
                        console.log("test==="+(eachCollab.id==this.bulletins[0].collaborateur.id));
                    });
                    let i: number = 0;

                    /*this.collaborateurs.forEach((eachCollab: Collaborateur) => {
                        let recap = new Recap();
                        for(let j=i; j<this.bulletins.length; j++){
                            recap.netAPayer += this.bulletins[j].netAPayer;
                            recap.brutFiscal += this.bulletins[j].brutFiscal;
                            recap.salaireBrutMensuel += this.bulletins[j].salaireBrutMensuel;
                            recap.impotSurRevenu += this.bulletins[j].impotSurRevenu;
                            recap.trimf += this.bulletins[j].trimf;
                            recap.ipresPartSalariale += this.bulletins[j].ipresPartSalariale;
                            recap.totRetenue += this.bulletins[j].totRetenue;
                            recap.ipresPartPatronales += this.bulletins[j].ipresPartPatronales;
                            recap.cssAccidentDeTravail += this.bulletins[j].cssAccidentDeTravail;
                            recap.cssPrestationFamiliale += this.bulletins[j].cssPrestationFamiliale;
                            recap.ipmPatronale += this.bulletins[j].ipmPatronale;
                            recap.contributionForfaitaire += this.bulletins[j].contributionForfaitaire;
                            recap.primeImposable += this.bulletins[j].primeImposable;
                            recap.primeNonImposable += this.bulletins[j].primeNonImposable;
                            recap. avantage+= this.bulletins[j].avantage;
                            recap.collaborateur = eachCollab;
                            recap.recapLigne = recap.brutFiscal + recap.ipresPartSalariale + recap.ipresPartPatronales;
                            this.recaps.push(recap);
                        }
                    });*/
                    this.collaborateurs.forEach((eachCollab: Collaborateur) => {
                        console.log("i==="+i);
                        let recap = new Recap();
                        for(let j=i; j<this.bulletins.length; j++){
                            if(eachCollab.id==this.bulletins[j].collaborateur.id){
                                recap.netAPayer += this.bulletins[j].netAPayer;
                                recap.brutFiscal += this.bulletins[j].brutFiscal;
                                recap.salaireBrutMensuel += this.bulletins[j].salaireBrutMensuel;
                                recap.impotSurRevenu += this.bulletins[j].impotSurRevenu;
                                recap.trimf += this.bulletins[j].trimf;
                                recap.ipresPartSalariale += this.bulletins[j].ipresPartSalariale;
                                recap.totRetenue += this.bulletins[j].totRetenue;
                                recap.ipresPartPatronales += this.bulletins[j].ipresPartPatronales;
                                recap.cssAccidentDeTravail += this.bulletins[j].cssAccidentDeTravail;
                                recap.cssPrestationFamiliale += this.bulletins[j].cssPrestationFamiliale;
                                recap.ipmPatronale += this.bulletins[j].ipmPatronale;
                                recap.contributionForfaitaire += this.bulletins[j].contributionForfaitaire;
                                recap.primeImposable += this.bulletins[j].primeImposable;
                                recap.primeNonImposable += this.bulletins[j].primeNonImposable;
                                recap. avantage+= this.bulletins[j].avantage;
                                recap.collaborateur = eachCollab;
                                recap.recapLigne = recap.brutFiscal + recap.ipresPartSalariale + recap.ipresPartPatronales;
                            }else{
                                i=j;
                                break;
                            }

                        }
                        this.recaps.push(recap);
                    });
                    for(let i=0;i<this.recaps.length;i++){
                        console.log("oui"+this.recaps[i].netAPayer);
                        if(this.recaps[i].netAPayer==0){
                            console.log("ouipppp");
                            this.recaps.splice(i,1);
                        }
                    }

                    console.log("***********************RESULTAT*****************")
                    this.recaps.forEach((it: Recap) => {
                        //console.log(it.netAPayer);
                        this.recapRsult.netAPayer+=it.netAPayer;
                        this.recapRsult.brutFiscal += it.brutFiscal;
                        this.recapRsult.salaireBrutMensuel += it.salaireBrutMensuel;
                        this.recapRsult.impotSurRevenu += it.impotSurRevenu;
                        this.recapRsult.trimf += it.trimf;
                        this.recapRsult.ipresPartSalariale += it.ipresPartSalariale;
                        this.recapRsult.totRetenue += it.totRetenue;
                        this.recapRsult.ipresPartPatronales += it.ipresPartPatronales;
                        this.recapRsult.cssAccidentDeTravail += it.cssAccidentDeTravail;
                        this.recapRsult.cssPrestationFamiliale += it.cssPrestationFamiliale;
                        this.recapRsult.ipmPatronale += it.ipmPatronale;
                        this.recapRsult.contributionForfaitaire += it.contributionForfaitaire;
                        this.recapRsult.primeImposable += it.primeImposable;
                        this.recapRsult.primeNonImposable += it.primeNonImposable;
                        this.recapRsult.avantage+= it.avantage;


                        console.log(it.collaborateur['prenom']+" "+it.collaborateur['nom']+" "+it.netAPayer);
                    });
                    this.recapRsult.recapLigne = this.recapRsult.brutFiscal + this.recapRsult.ipresPartSalariale + this.recapRsult.ipresPartPatronales;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    downLoadPdf(){
        if(this.recaps.length<=0)return;
        let data = document.getElementById ('myPiece');
        html2canvas(data).then(canvas =>{
            let imgWidth = 208;
            //let pageHeight = 295;
            let imgHeight = canvas.height * imgWidth / canvas.width;
            //let heightLeft = imgHeight;

            const contentDataURL = canvas.toDataURL('image/png');
            let pdf = new jsPDF('p', 'mm', 'a4');
            pdf.addImage(contentDataURL, 'PNG', 10, 20, imgWidth, imgHeight);
            pdf.save("gestionBudgetaire"+'.pdf');
        });
    }
}
