import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Structure } from './structure.model';
import { StructurePopupService } from './structure-popup.service';
import { StructureService } from './structure.service';
import { Convention, ConventionService } from '../convention';

@Component({
    selector: 'jhi-structure-dialog',
    templateUrl: './structure-dialog.component.html'
})
export class StructureDialogComponent implements OnInit {

    structure: Structure;
    isSaving: boolean;

    conventions: Convention[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private structureService: StructureService,
        private conventionService: ConventionService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.conventionService.query()
            .subscribe((res: HttpResponse<Convention[]>) => { this.conventions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.structure, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.structure.id !== undefined) {
            this.subscribeToSaveResponse(
                this.structureService.update(this.structure));
        } else {
            this.subscribeToSaveResponse(
                this.structureService.create(this.structure));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Structure>>) {
        result.subscribe((res: HttpResponse<Structure>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Structure) {
        this.eventManager.broadcast({ name: 'structureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConventionById(index: number, item: Convention) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-structure-popup',
    template: ''
})
export class StructurePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private structurePopupService: StructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.structurePopupService
                    .open(StructureDialogComponent as Component, params['id']);
            } else {
                this.structurePopupService
                    .open(StructureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
