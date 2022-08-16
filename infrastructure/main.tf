# Backend
terraform {
  required_version = ">= 0.14"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
  backend "s3" {
    encrypt = true
  }
}

provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile
}

locals {
  cannonical_name = "${var.name}-${var.environment}"
}

# Project variables

variable "name" {
  description = "Optional name that will be used as suffix for every AWS resource created by this script."
}

variable "environment" {
  description = "An environment deployment identifier."
}

# AWS variables

variable "aws_region" {
  description = "AWS Region to be used by this deployment script"
}

variable "aws_vpc_id" {
  description = "VPC ID in which this setup should rely on"
}

variable "aws_profile" {
  description = "AWS Profile which contains the credentials to be used by this deployment script"
}

# Code Artifact

variable "domain" {
  description = "Domain for maven repository"
}

variable "repository" {
  description = "Name for maven repository"
}