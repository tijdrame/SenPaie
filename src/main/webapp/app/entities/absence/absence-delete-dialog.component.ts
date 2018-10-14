import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Absence } from './absence.model';
import { AbsencePopupService } from './absence-popup.service';
import { AbsenceService } from './absence.service';

@Component({
    selector: 'jhi-absence-delete-dialog',
    templateUrl: './absence-delete-dialog.component.html'
})
export class AbsenceDeleteDialogComponent {

    absence: Absence;

    constructor(
        private absenceService: AbsenceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.absenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'absenceListModification',
                content: 'Deleted an absence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-absence-delete-popup',
    template: ''
})
export class AbsenceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private absencePopupService: AbsencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.absencePopupService
                .open(AbsenceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
