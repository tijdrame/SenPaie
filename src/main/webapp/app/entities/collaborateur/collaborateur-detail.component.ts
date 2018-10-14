import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Collaborateur } from './collaborateur.model';
import { CollaborateurService } from './collaborateur.service';

@Component({
    selector: 'jhi-collaborateur-detail',
    templateUrl: './collaborateur-detail.component.html'
})
export class CollaborateurDetailComponent implements OnInit, OnDestroy {

    collaborateur: Collaborateur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private collaborateurService: CollaborateurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCollaborateurs();
    }

    load(id) {
        this.collaborateurService.find(id)
            .subscribe((collaborateurResponse: HttpResponse<Collaborateur>) => {
                this.collaborateur = collaborateurResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCollaborateurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'collaborateurListModification',
            (response) => this.load(this.collaborateur.id)
        );
    }
}
