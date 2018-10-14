import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbsence } from './type-absence.model';
import { TypeAbsencePopupService } from './type-absence-popup.service';
import { TypeAbsenceService } from './type-absence.service';

@Component({
    selector: 'jhi-type-absence-delete-dialog',
    templateUrl: './type-absence-delete-dialog.component.html'
})
export class TypeAbsenceDeleteDialogComponent {

    typeAbsence: TypeAbsence;

    constructor(
        private typeAbsenceService: TypeAbsenceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeAbsenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeAbsenceListModification',
                content: 'Deleted an typeAbsence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-absence-delete-popup',
    template: ''
})
export class TypeAbsenceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeAbsencePopupService: TypeAbsencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeAbsencePopupService
                .open(TypeAbsenceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
