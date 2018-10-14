import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Collaborateur } from './collaborateur.model';
import { CollaborateurPopupService } from './collaborateur-popup.service';
import { CollaborateurService } from './collaborateur.service';

@Component({
    selector: 'jhi-collaborateur-delete-dialog',
    templateUrl: './collaborateur-delete-dialog.component.html'
})
export class CollaborateurDeleteDialogComponent {

    collaborateur: Collaborateur;

    constructor(
        private collaborateurService: CollaborateurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.collaborateurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'collaborateurListModification',
                content: 'Deleted an collaborateur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-collaborateur-delete-popup',
    template: ''
})
export class CollaborateurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collaborateurPopupService: CollaborateurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.collaborateurPopupService
                .open(CollaborateurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
