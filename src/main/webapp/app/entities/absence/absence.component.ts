import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Absence } from './absence.model';
import { AbsenceService } from './absence.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Collaborateur, CollaborateurService} from "../collaborateur";
import {Exercice, ExerciceService} from "../exercice";
import {Motif, MotifService} from "../motif";
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';

@Component({
    selector: 'jhi-absence',
    templateUrl: './absence.component.html'
})
export class AbsenceComponent implements OnInit, OnDestroy {

currentAccount: any;
    absences: Absence[];
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
    collaborateurs: Collaborateur[];
    collaborateur: Collaborateur;
    exercices: Exercice[];
    exercice: Exercice;
    motif: Motif;
    motifs: Motif[];
    nbAbsence: number = 0;

    constructor(
        private absenceService: AbsenceService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private collaborateurService: CollaborateurService,
        private motifService: MotifService,
        private exerciceService: ExerciceService
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
        /*this.absenceService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<Absence[]>) => this.onSuccess(res.body, res.headers),
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
        this.router.navigate(['/absence'], {queryParams:
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
        this.router.navigate(['/absence', {
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
        this.registerChangeInAbsences();
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
        this.exerciceService.query()
            .subscribe((res: HttpResponse<Exercice[]>) => { this.exercices = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
        this.motifService.query()
            .subscribe((res: HttpResponse<Motif[]>) => { this.motifs = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
    }

    search(){
        this.absenceService.search(this.collaborateur.id, this.exercice.id, this.motif.id)
            .subscribe((res: HttpResponse<Absence[]>) => { this.absences = res.body;
            this.nbAbsence = this.absences.length;
                },
                (res: HttpErrorResponse) => this.onError(res.message));


    }

    downLoadPdf(){
        let data = document.getElementById ('myAbs');
        html2canvas(data).then(canvas =>{
            let imgWidth = 208;
            let imgHeight = canvas.height * imgWidth / canvas.width;
            const contentDataURL = canvas.toDataURL('image/png');
            let pdf = new jsPDF('p', 'mm', 'a4');
            pdf.addImage(contentDataURL, 'PNG', 10, 20, imgWidth, imgHeight);
            pdf.save('listAbsences.pdf');
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Absence) {
        return item.id;
    }
    registerChangeInAbsences() {
        this.eventSubscriber = this.eventManager.subscribe('absenceListModification', (response) => this.loadAll());
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
        this.absences = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
