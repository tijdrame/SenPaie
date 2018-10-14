import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Remboursement } from './remboursement.model';
import { RemboursementService } from './remboursement.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {isUndefined} from "util";
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';

@Component({
    selector: 'jhi-remboursement',
    templateUrl: './remboursement.component.html'
})
export class RemboursementComponent implements OnInit, OnDestroy {

currentAccount: any;
    remboursements: Remboursement[];
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

    constructor(
        private remboursementService: RemboursementService,
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

    downLoadPdf(){
        if(!this.remboursements)return;
        let data = document.getElementById ('myAbs');
        html2canvas(data).then(canvas =>{
            let imgWidth = 208;
            let imgHeight = canvas.height * imgWidth / canvas.width;
            const contentDataURL = canvas.toDataURL('image/png');
            let pdf = new jsPDF('p', 'mm', 'a4');
            pdf.addImage(contentDataURL, 'PNG', 5, 20, imgWidth, imgHeight);
            pdf.save('listRemboursements.pdf');
        });
    }

    loadAll() {
        /*this.remboursementService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<Remboursement[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );*/
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    search(){
        if(this.prenom===""||isUndefined(this.prenom))this.prenom=" ";
        if(this.nom===""||isUndefined(this.nom))this.nom=" ";
        if(this.matricule===""||isUndefined(this.matricule))this.matricule=" ";
        this.remboursementService.search(this.prenom, this.nom, this.matricule )
            .subscribe((res: HttpResponse<Remboursement[]>) => {

                this.remboursements = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    transition() {
        this.router.navigate(['/remboursement'], {queryParams:
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
        this.router.navigate(['/remboursement', {
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
        this.registerChangeInRemboursements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Remboursement) {
        return item.id;
    }
    registerChangeInRemboursements() {
        this.eventSubscriber = this.eventManager.subscribe('remboursementListModification', (response) => this.loadAll());
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
        this.remboursements = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
