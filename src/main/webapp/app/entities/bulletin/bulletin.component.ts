import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Pieces} from "../pieces/pieces.model";
import {isUndefined} from "util";
import {isBlank} from "ngx-cookie";
import {MoisConcerne, MoisConcerneService} from "../mois-concerne";
import {Exercice, ExerciceService} from "../exercice";



@Component({
    selector: 'jhi-bulletin',
    templateUrl: './bulletin.component.html'
})
export class BulletinComponent implements OnInit, OnDestroy {

currentAccount: any;
    bulletins: Bulletin[];
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
    prenom: string;
    nom: string;
    matricule: string;
    theDate: any;
    deleted: boolean = false;
    moisConcerne: MoisConcerne;
    moisConcernes: MoisConcerne[];
    exercice: Exercice;
    exercices: Exercice[];

    constructor(
        private bulletinService: BulletinService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private moisService: MoisConcerneService,
        private exerciceService: ExerciceService,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
       /* this.bulletinService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<Bulletin[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );*/
    }

    search(){
        console.log("prenom="+this.prenom);
        //if(this.prenom=="")this.prenom=" ";
        if(this.prenom===""||isUndefined(this.prenom))this.prenom=" ";
        if(this.nom===""||isUndefined(this.nom))this.nom=" ";
        if(this.matricule===""||isUndefined(this.matricule))this.matricule=" ";
        console.log("date form=====>"+new Date().toLocaleString());
        //{"year":2018,"month":10,"day":3}
        //console.log("date form=====>"+this.theDate['year']);
        //let dateBi =this.theDate['day']+"-"+this.theDate['month']+"-"+this.theDate['year'];

        console.log("dans search collab..2"+this.prenom+"a"+isUndefined(this.deleted)+" "+this.deleted);
        this.bulletinService.search(this.prenom, this.nom, this.matricule, this.deleted, this.moisConcerne, this.exercice )
            .subscribe((res: HttpResponse<Bulletin[]>) => { this.bulletins = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/bulletin'], {queryParams:
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
        this.router.navigate(['/bulletin', {
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
        this.registerChangeInBulletins();

        this.moisService.query()
            .subscribe((res: HttpResponse<MoisConcerne[]>) => { this.moisConcernes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.exerciceService.query()
            .subscribe((res: HttpResponse<Exercice[]>) => { this.exercices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Bulletin) {
        return item.id;
    }

    trackExerciceById(index: number, item: Exercice) {
        return item.id;
    }
    registerChangeInBulletins() {
        this.eventSubscriber = this.eventManager.subscribe('bulletinListModification', (response) => this.loadAll());
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
        this.bulletins = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
