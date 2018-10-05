import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Exercice } from './exercice.model';
import { ExercicePopupService } from './exercice-popup.service';
import { ExerciceService } from './exercice.service';

@Component({
    selector: 'jhi-exercice-delete-dialog',
    templateUrl: './exercice-delete-dialog.component.html'
})
export class ExerciceDeleteDialogComponent {

    exercice: Exercice;

    constructor(
        private exerciceService: ExerciceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.exerciceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'exerciceListModification',
                content: 'Deleted an exercice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exercice-delete-popup',
    template: ''
})
export class ExerciceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private exercicePopupService: ExercicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.exercicePopupService
                .open(ExerciceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
