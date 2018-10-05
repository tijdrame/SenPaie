import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeRelationComponent } from './type-relation.component';
import { TypeRelationDetailComponent } from './type-relation-detail.component';
import { TypeRelationPopupComponent } from './type-relation-dialog.component';
import { TypeRelationDeletePopupComponent } from './type-relation-delete-dialog.component';

@Injectable()
export class TypeRelationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const typeRelationRoute: Routes = [
    {
        path: 'type-relation',
        component: TypeRelationComponent,
        resolve: {
            'pagingParams': TypeRelationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.typeRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-relation/:id',
        component: TypeRelationDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.typeRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeRelationPopupRoute: Routes = [
    {
        path: 'type-relation-new',
        component: TypeRelationPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.typeRelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-relation/:id/edit',
        component: TypeRelationPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.typeRelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-relation/:id/delete',
        component: TypeRelationDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.typeRelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
