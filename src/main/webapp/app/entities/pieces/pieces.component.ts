import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Pieces } from './pieces.model';
import { PiecesService } from './pieces.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Collaborateur} from "../collaborateur/collaborateur.model";
import {isUndefined} from "util";

@Component({
    selector: 'jhi-pieces',
    templateUrl: './pieces.component.html'
})
export class PiecesComponent implements OnInit, OnDestroy {

currentAccount: any;
    pieces: Pieces[];
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
    deleted: boolean = false;

    constructor(
        private piecesService: PiecesService,
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
        /*this.piecesService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<Pieces[]>) => this.onSuccess(res.body, res.headers),
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
        this.router.navigate(['/pieces'], {queryParams:
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
        this.router.navigate(['/pieces', {
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
        this.registerChangeInPieces();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pieces) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    search(){
        if(this.prenom===""||isUndefined(this.prenom))this.prenom=" ";
        if(this.nom===""||isUndefined(this.nom))this.nom=" ";
        if(this.matricule===""||isUndefined(this.matricule))this.matricule=" ";
        console.log("dans search collab..2"+this.prenom+"a"+isUndefined(this.prenom));
        this.piecesService.search(this.prenom, this.nom, this.matricule, this.deleted )
            .subscribe((res: HttpResponse<Pieces[]>) => { this.pieces = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInPieces() {
        this.eventSubscriber = this.eventManager.subscribe('piecesListModification', (response) => this.loadAll());
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
        this.pieces = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
