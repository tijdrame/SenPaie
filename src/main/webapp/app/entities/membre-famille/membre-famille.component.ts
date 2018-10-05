import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MembreFamille } from './membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Collaborateur} from "../collaborateur/collaborateur.model";
import {isUndefined} from "util";

@Component({
    selector: 'jhi-membre-famille',
    templateUrl: './membre-famille.component.html'
})
export class MembreFamilleComponent implements OnInit, OnDestroy {

currentAccount: any;
    membreFamilles: MembreFamille[];
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
    deleted: boolean = false;

    constructor(
        private membreFamilleService: MembreFamilleService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private dataUtils: JhiDataUtils,
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
        /*this.membreFamilleService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<MembreFamille[]>) => this.onSuccess(res.body, res.headers),
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
        this.router.navigate(['/membre-famille'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    search(){
        if(isUndefined(this.prenom))this.prenom=" ";
        if(isUndefined(this.nom))this.nom=" ";
        if(isUndefined(this.telephone))this.telephone=" ";
        console.log("dans search collab..2"+this.prenom+"a"+isUndefined(this.prenom));
        this.membreFamilleService.search(this.prenom, this.nom, this.telephone, this.deleted )
            .subscribe((res: HttpResponse<MembreFamille[]>) => { this.membreFamilles = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
        /*this.prenom=" ";
        this.nom=" ";
        this.telephone=" ";*/
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/membre-famille', {
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
        this.registerChangeInMembreFamilles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MembreFamille) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMembreFamilles() {
        this.eventSubscriber = this.eventManager.subscribe('membreFamilleListModification', (response) => this.loadAll());
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
        this.membreFamilles = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
