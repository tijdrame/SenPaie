import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Sexe } from './sexe.model';
import { SexeService } from './sexe.service';

@Component({
    selector: 'jhi-sexe-detail',
    templateUrl: './sexe-detail.component.html'
})
export class SexeDetailComponent implements OnInit, OnDestroy {

    sexe: Sexe;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sexeService: SexeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSexes();
    }

    load(id) {
        this.sexeService.find(id)
            .subscribe((sexeResponse: HttpResponse<Sexe>) => {
                this.sexe = sexeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSexes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sexeListModification',
            (response) => this.load(this.sexe.id)
        );
    }
}
