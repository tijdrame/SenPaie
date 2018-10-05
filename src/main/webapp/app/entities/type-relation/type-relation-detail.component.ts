import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeRelation } from './type-relation.model';
import { TypeRelationService } from './type-relation.service';

@Component({
    selector: 'jhi-type-relation-detail',
    templateUrl: './type-relation-detail.component.html'
})
export class TypeRelationDetailComponent implements OnInit, OnDestroy {

    typeRelation: TypeRelation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeRelationService: TypeRelationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeRelations();
    }

    load(id) {
        this.typeRelationService.find(id)
            .subscribe((typeRelationResponse: HttpResponse<TypeRelation>) => {
                this.typeRelation = typeRelationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeRelations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeRelationListModification',
            (response) => this.load(this.typeRelation.id)
        );
    }
}
