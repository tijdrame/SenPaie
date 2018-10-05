import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SituationMatrimoniale } from './situation-matrimoniale.model';
import { SituationMatrimonialePopupService } from './situation-matrimoniale-popup.service';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';

@Component({
    selector: 'jhi-situation-matrimoniale-delete-dialog',
    templateUrl: './situation-matrimoniale-delete-dialog.component.html'
})
export class SituationMatrimonialeDeleteDialogComponent {

    situationMatrimoniale: SituationMatrimoniale;

    constructor(
        private situationMatrimonialeService: SituationMatrimonialeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.situationMatrimonialeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'situationMatrimonialeListModification',
                content: 'Deleted an situationMatrimoniale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-situation-matrimoniale-delete-popup',
    template: ''
})
export class SituationMatrimonialeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private situationMatrimonialePopupService: SituationMatrimonialePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.situationMatrimonialePopupService
                .open(SituationMatrimonialeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
