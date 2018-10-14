import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { DemandeConge } from './demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Collaborateur} from "../collaborateur/collaborateur.model";
import {isUndefined} from "util";

@Component({
    selector: 'jhi-demande-conge',
    templateUrl: './demande-conge.component.html'
})
export class DemandeCongeComponent implements OnInit, OnDestroy {

currentAccount: any;
    demandeConges: DemandeConge[];
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
    telephone: string;
    dateDebut: any;

    constructor(
        private demandeCongeService: DemandeCongeService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
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
        this.demandeCongeService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<DemandeConge[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/demande-conge'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    search(){
        //console.log(JSON.stringify(this.dateDebut)+" date bi");

        let dateDeb:string;
        //new Date(this.dateDebut['year'], this.dateDebut['month'], this.dateDebut['day']);
        if(this.prenom===""||isUndefined(this.prenom))this.prenom=" ";
        if(this.nom===""||isUndefined(this.nom))this.nom=" ";
        if(this.telephone===""||isUndefined(this.telephone))this.telephone=" ";
        /*if(isUndefined(this.dateDebut)){
            this.dateDebut="";
             dateDeb="";
        }else{
            dateDeb = this.dateDebut['year']+"-"+this.dateDebut['month']+"-"+this.dateDebut['day'];
            if(this.dateDebut['month']<10)this.dateDebut['month'] = "0"+this.dateDebut['month'];
            if(this.dateDebut['day']<10)this.dateDebut['day'] = "0"+this.dateDebut['day'];
        }*/

        this.demandeCongeService.search(this.prenom, this.nom, this.telephone)
            .subscribe((res: HttpResponse<DemandeConge[]>) => { this.demandeConges = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
        //this.prenom=" ";
        //this.nom=" ";
        //this.telephone=" ";
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/demande-conge', {
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
        this.registerChangeInDemandeConges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DemandeConge) {
        return item.id;
    }
    registerChangeInDemandeConges() {
        this.eventSubscriber = this.eventManager.subscribe('demandeCongeListModification', (response) => this.loadAll());
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
        this.demandeConges = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
