import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Fonction } from './fonction.model';
import { FonctionService } from './fonction.service';

@Component({
    selector: 'jhi-fonction-detail',
    templateUrl: './fonction-detail.component.html'
})
export class FonctionDetailComponent implements OnInit, OnDestroy {

    fonction: Fonction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fonctionService: FonctionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFonctions();
    }

    load(id) {
        this.fonctionService.find(id)
            .subscribe((fonctionResponse: HttpResponse<Fonction>) => {
                this.fonction = fonctionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFonctions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fonctionListModification',
            (response) => this.load(this.fonction.id)
        );
    }
}
